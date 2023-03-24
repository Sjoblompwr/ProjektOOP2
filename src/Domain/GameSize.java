package Domain;
public enum GameSize {
    SMALL(750,750)
    ,MEDIUM(1000,1000)
    ,LARGE(1250,1250);

    private int width;
    private int height;

    private GameSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
