package MouseHandler;

import java.awt.Point;

public interface Pointable
  {
  public void pointerDown(Point point);
  public void pointerMoved(Point point, boolean pointerDown);
  }
