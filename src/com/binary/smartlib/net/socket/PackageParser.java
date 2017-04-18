import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yaoguoju on 1-4-18.
 */
public enum  PackageParser {
    PARSER;
    private static final int HEADER_SIZE = 6;
    private List<Header> headerList = new ArrayList<Header>();
    private Header temp;
    private int readlen;
    private String foler = "/home/yaoguoju";
    private FileOutputStream fos;
    private boolean attach = false;
    private byte[] surplus = new byte[Protocol.SOCKET_BUFFER_SIZE];
    private int surpluslen = 0;
    private String path;

    public void printHeader() {
        System.out.println(headerList);
    }

    public void parse(byte[] bytes,int len) throws Exception {
        if(temp != null) {
            int length = temp.getLength();
            Byte type = temp.getMimeType();
            System.out.println("length  "+length+",type "+type);
            if(attach) {
                int gap = length - readlen;
                System.out.println("attach gap "+gap+",len "+len);
                if(len >= gap) {
                    fos.write(bytes,0,gap);
                    fos.flush();
                    fos.close();
                    attach = false;
                    temp.setPath(path);
                    headerList.add(temp);
                    temp = null;
                    readlen = 0;
                    surpluslen = 0;
                    if((len-gap) >= HEADER_SIZE) {
                        parse(Arrays.copyOfRange(bytes,gap,len),len-gap);
                        return;
                    }else {
                        surpluslen = len-gap;
                        System.arraycopy(bytes,gap,surplus,0,len-gap);
                    }
                }else {
                    fos.write(bytes,0,len);
                    readlen += len;
                }
            }else {
                if (type == 0x01) {
                    path = foler+File.separator+System.currentTimeMillis()+"_"+temp.getLength()+".txt";
                    fos = new FileOutputStream(path);
                }
                if (length <= (len - HEADER_SIZE)) {
                    fos.write(bytes, HEADER_SIZE, length);
                    fos.flush();
                    fos.close();
                    attach = false;
                    temp.setPath(path);
                    headerList.add(temp);
                    temp = null;
                    readlen = 0;
                    surpluslen = 0;
                    if((len-HEADER_SIZE-length) >= HEADER_SIZE) {
                        parse(Arrays.copyOfRange(bytes,length+6,len),len-6-length);
                        return ;
                    }else {
                        surpluslen = len-HEADER_SIZE-length;
                        System.arraycopy(bytes,length+6,surplus,0,len-HEADER_SIZE-length);
                    }
                } else {
                    fos.write(bytes, HEADER_SIZE, len - HEADER_SIZE);
                    readlen = len - HEADER_SIZE;
                    attach = true;
                }
            }
        }else {
            if(surpluslen != 0) {
                byte[] data = new byte[surpluslen+len];
                System.arraycopy(surplus,0,data,0,surpluslen);
                System.arraycopy(bytes,0,data,surpluslen,len);
                temp= Protocol.parseHeader(data);
                if(temp!=null) {
                    parse(data,surpluslen+len);
                }
            }else {
                temp = Protocol.parseHeader(bytes);
                if (temp != null) {
                    parse(bytes, len);
                }
            }
        }

    }


}
