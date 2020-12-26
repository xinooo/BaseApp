package com.gwtsz.android.rxbus.event;

/**
 * Created by spence on 17-4-21.
 */

public class RequestHttpInformation<T> {

  private int requestCode;

  private T parm;

  public RequestHttpInformation(int RequestCode) {
    requestCode=RequestCode;
  }

  public int getRequestCode() {
    return requestCode;
  }

  public void setRequestCode(int requestCode) {
    this.requestCode = requestCode;
  }

  public T getParm() {
    return parm;
  }

  public void setParm(T parm) {
    this.parm = parm;
  }
}
