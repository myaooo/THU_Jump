package com.libgdx.jump;

import java.util.ArrayList;

import com.libgdx.jump.gfx.AnimatedImage;
import com.libgdx.jump.gfx.LoadedFont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MenuHandler {

	private ArrayList<MenuItem> items;
	private int selected;
	private boolean keyPressed;
	private boolean enterPressed;
	private boolean leftClicked;

	private final Layout layout;
	private final Handle handle;
	private final int startX;
	private final int startY;

	public MenuHandler(Layout layout, Handle handle, int startX, int startY) {
		items = new ArrayList<MenuHandler.MenuItem>();
		this.layout = layout;
		this.handle = handle;
		this.startX = startX;
		this.startY = startY;
		selected = 0;
		keyPressed = false;
		enterPressed = false;
		leftClicked = false;
	}

	public void addMenuItem(String name, AnimatedImage image, LoadedFont font, Runnable action) {
		if (layout == Layout.Unformatted) {
			System.out.println("Warning - You should use the other addMenuItem method! (Nothing added)");
			return;
		}
		int tmpx, tmpy;
		switch (layout) {
		case Horizonal:
			tmpx = items.size() * (image.getWidth() + 10) + startX;
			tmpy = startY;
			break;
		case Vertical:
			tmpx = startX;
			tmpy = startY - items.size() * (image.getHeight() + 10);
			break;
		default:
			tmpx = tmpy = 0;
		}
		items.add(new MenuItem(name, image, font, action, tmpx, tmpy, image.getWidth(), image.getHeight()));
	}

	public void addMenuItem(String name, AnimatedImage image, LoadedFont font, Runnable action, int x, int y) {
		if (layout != Layout.Unformatted) {
			System.out.println("Warning - You should use the other addMenuItem method! (Nothing added)");
			return;
		}
		items.add(new MenuItem(name, image, font, action, x, y, image.getWidth(), image.getHeight()));
	}

	public void draw(SpriteBatch batch) {
		for (MenuItem item : items) {
			if (item.equals(items.get(selected))) {
				batch.draw(item.image.getSpecificImage(1), item.x, item.y);
			} else {
				batch.draw(item.image.getSpecificImage(0), item.x, item.y);
			}
			item.font.drawCenter(batch, item.name, item.x + item.image.getWidth() / 2, item.y + item.image.getHeight() / 2);
		}
	}

	public void handle(OrthographicCamera camera, int currentWidth, int currentHeight) {
		if (items.size() < 1) {
			System.out.println("Warning - empty MenuHandler. Fill it up!");
			return;
		}
		if (handle == Handle.Keyboard || handle == Handle.KeyboardAndMouse)
			keyboardHandler();
		if (handle == Handle.Mouse || handle == Handle.KeyboardAndMouse)
			mouseHandler(camera, currentWidth, currentHeight);
	}

	public String getSelectedName() {
		return items.get(selected).name;
	}

	public int getFirstX() {
		return items.get(0).x;
	}

	public int getFirstY() {
		return items.get(0).y;
	}

	private void keyboardHandler() {
		handling: {
			if (layout == Layout.Vertical || layout == Layout.Unformatted) {
				if (Gdx.input.isKeyPressed(Keys.DOWN)) {
					if (keyPressed)
						break handling;
					if (++selected > items.size() - 1) {
						selected = items.size() - 1;
					}
					keyPressed = true;
				} else if (Gdx.input.isKeyPressed(Keys.UP)) {
					if (keyPressed)
						break handling;
					if (--selected < 0) {
						selected = 0;
					}
					keyPressed = true;
				} else {
					keyPressed = false;
				}
			}

			if (layout == Layout.Horizonal || layout == Layout.Unformatted) {
				if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
					if (keyPressed)
						break handling;
					if (++selected > items.size() - 1) {
						selected = items.size() - 1;
					}
					keyPressed = true;
				} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
					if (keyPressed)
						break handling;
					if (--selected < 0) {
						selected = 0;
					}
					keyPressed = true;
				} else {
					keyPressed = false;
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.ENTER) && !enterPressed) {
			enterPressed = true;
		} else if (!Gdx.input.isKeyPressed(Keys.ENTER) && enterPressed) {
			if (Options.isSoundEnabled())
				Res._s_click.play();
			items.get(selected).action.run();
			enterPressed = false;
		}
	}

	private void mouseHandler(OrthographicCamera camera, int currentWidth, int currentHeight) {
		float widthScale = camera.viewportWidth / currentWidth;
		float heightScale = camera.viewportHeight / currentHeight;
		Rectangle mouseRect = new Rectangle(Gdx.input.getX() * widthScale, currentHeight * heightScale - Gdx.input.getY() * heightScale, 1, 1);
		searcher: for (MenuItem item : items) {
			if (item.rec.overlaps(mouseRect)) {
				selected = items.indexOf(item);
				break searcher;
			}
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !leftClicked) {
			leftClicked = true;
		} else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && leftClicked) {
			if (items.get(selected).rec.overlaps(mouseRect)) {
				if (Options.isSoundEnabled())
					Res._s_click.play();
				items.get(selected).action.run();
			}
			leftClicked = false;
		}
	}

	// Additional enum, class

	private class MenuItem {
		private final Runnable action;
		private final String name;
		private final AnimatedImage image;
		private final LoadedFont font;
		private final int x;
		private final int y;
		private final Rectangle rec;

		public MenuItem(String name, AnimatedImage image, LoadedFont font, Runnable action, int x, int y, int menuWidth, int menuHeight) {
			if (action == null) {
				System.out.println("Warning: The added menu item's action shouldn't be null! Setting up an empty action.");
				action = new Runnable() {
					@Override
					public void run() {
					}
				};
			}
			this.name = name;
			this.image = image;
			this.font = font;
			this.action = action;
			this.x = x;
			this.y = y;
			rec = new Rectangle(x, y, menuWidth, menuHeight);
		}
	}

	public static enum Layout {
		Horizonal, Vertical, Unformatted;
	}

	public static enum Handle {
		Keyboard, Mouse, KeyboardAndMouse;
	}

}
