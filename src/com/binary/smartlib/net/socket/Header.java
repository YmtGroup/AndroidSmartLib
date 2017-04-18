/**
 * Created by yaoguoju on 17-4-18.
 */
public class Header {
    private Byte cmdID;
    private Byte mimeType;
    private int length;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public Byte getCmdID() {
        return cmdID;
    }

    public void setCmdID(Byte cmdID) {
        this.cmdID = cmdID;
    }

    public Byte getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return "Header{" +
                "cmdID=" + cmdID +
                ", mimeType=" + mimeType +
                ", length=" + length +
                ", path='" + path + '\'' +
                '}';
    }

    public void setMimeType(Byte mimeType) {
        this.mimeType = mimeType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
