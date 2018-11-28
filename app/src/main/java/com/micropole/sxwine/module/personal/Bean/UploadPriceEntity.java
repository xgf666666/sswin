package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/15.
 */

public class UploadPriceEntity {

    public UploadPriceEntity(String file_path){
        this.file_path=file_path;
    }
    /**
     * file_path : /uploads/user_imgs/20180611/10d86b1f7bc10050e7224599ba775023.jpg
     */

    private String file_path;

    public String getNew_avatar() {
        return new_avatar;
    }

    public void setNew_avatar(String new_avatar) {
        this.new_avatar = new_avatar;
    }

    private String new_avatar;

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
