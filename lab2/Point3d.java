import static java.lang.Math.*;
public class Point3d {
    /** координата X **/
    private double xCoord;
    /** координата Y **/
    private double yCoord;
    /** координата Z **/
    private double zCoord;
    /** Конструктор инициализации **/
    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
      }
    /** Конструктор по умолчанию. **/
    public Point3d() {
        //Вызовите конструктор с двумя параметрами и определите источник.
        this(0, 0, 0);
      }
    /** Возвращение координаты X **/
    public double getX() {
        return xCoord;
      }
    /** Возвращение координаты Y **/
    public double getY() {
        return yCoord;
      }
    /** Возвращение координаты Z **/
    public double getZ() {
          return zCoord;
      }
    /** Установка значения координаты X. **/
    public void setX( double val) {
        xCoord = val;
      }
    /** Установка значения координаты Y. **/
    public void setY(double val) {
        yCoord = val;
      }

    /** Установка значения координаты Z. **/
    public void setZ( double val) {
        zCoord = val;
      }
    /* public boolean equals(Point3d p)
    {
        return p.getX() == xCoord && p.getY() == yCoord && p.getZ() == zCoord;
    } */
    public static boolean equals (Point3d p1, Point3d p2)
    {
        return p1.getX() == p2.getX() && p1.getY() == p2.getY() &&
               p1.getZ() == p2.getZ();
    }
    public double distanceTo (Point3d p)
    {
        return sqrt(pow((p.getX() - xCoord), 2) +
                    pow((p.getY() - yCoord), 2) +
                    pow((p.getZ() - zCoord), 2));
    }
  }
