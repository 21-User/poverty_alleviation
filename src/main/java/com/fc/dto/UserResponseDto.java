package com.fc.dto;

import com.github.pagehelper.PageInfo;

public class UserResponseDto {

    private String message;
    private Integer code;
    private boolean success;
    private PageInfo<?> data;

    public UserResponseDto(String message, Integer code, boolean success, PageInfo<?> data) {
        this.message = message;
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public UserResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PageInfo<?> getData() {
        return data;
    }

    public void setData(PageInfo<?> data) {
        this.data = data;
    }

    public static UserResponseDto getUserMessageSuccess(PageInfo<?> data) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setMessage("用户获取成功！");
        responseDto.setCode(200);
        responseDto.setSuccess(true);
        responseDto.setData(data);


        return responseDto;
    }

    public static UserResponseDto getUserMessageFail() {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setMessage("用户获取失败！");
        responseDto.setCode(500);
        responseDto.setSuccess(false);
        responseDto.setData(null);

        return responseDto;
    }

}
