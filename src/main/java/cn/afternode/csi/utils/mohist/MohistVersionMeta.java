package cn.afternode.csi.utils.mohist;

import com.google.gson.annotations.SerializedName;

public class MohistVersionMeta {
    public String status;
    public int number;
    public String version;
    public String name;

    @SerializedName("md5")
    public String hash;

    public String url;
    public String mirror;
}
