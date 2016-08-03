package helper;

/**
 * Created by Zephyr on 2016/8/3.
 */
public class FaceItemData {
    private @DataHelper.IconType int type;
    private int position;
    private int id;

    public FaceItemData() {
    }

    public FaceItemData(int type, int position, int id) {
        this.type = type;
        this.position = position;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
