import java.util.Arrays;

/**
 * Created by yaoguoju on 17-4-18.
 */
public class Protocol {

    public static final int SOCKET_BUFFER_SIZE  = 1024;
    /**
     * cmdid   0x00:media share 0x01:upload
     * mimetyte 0x01:jpg 0x02:png 0x03:mp3 0x04:mp4
     * length   0x01010102
     * content:
     * @param data
     */
    public static Header parseHeader(byte[] data) {
        int len = data.length;
        Header header = null;
        if(len >= 6) {
            header = new Header();
            header.setCmdID(data[0]);
            header.setMimeType(data[1]);
            int length = byteArrayToInt(Arrays.copyOfRange(data,2,6));
            header.setLength(length);
        }
        System.out.println("parseHeader "+header);
        return header;
    }

    public static byte[] headerByte(Header header) {
        byte[] h = new byte[6];
        h[0] = header.getCmdID();
        h[1] = header.getMimeType();
        byte[] len = intToByteArray(header.getLength());
        System.arraycopy(len,0,h,2,4);
        return h;
    }

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

}
