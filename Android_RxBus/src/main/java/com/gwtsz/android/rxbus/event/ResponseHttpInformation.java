package com.gwtsz.android.rxbus.event;

/**
 * Created by spence on 17-4-21.
 */

public class ResponseHttpInformation {

  private int responseCode;

  private int responseState;

  private String responseInformation;

  public ResponseHttpInformation(int responseCode, int responseState,String responseInformation) {
    this.responseCode = responseCode;
    this.responseState = responseState;
    this.responseInformation=responseInformation;
  }

  public int getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseState() {
    return responseState;
  }

  public void setResponseState(int responseState) {
    this.responseState = responseState;
  }

  public String getResponseInformation() {
    return responseInformation;
  }

  public void setResponseInformation(String responseInformation) {
    this.responseInformation = responseInformation;
  }
}
