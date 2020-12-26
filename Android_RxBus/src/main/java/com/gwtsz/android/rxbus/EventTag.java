package com.gwtsz.android.rxbus;

/**
 *
 * Created by spence on 17-4-21.
 */

public class EventTag {

  public final static String HTTP_REQUEST="http_request";

  public final static String HTTP_RESPONSE="http_response";

  public final static String INFORMATION="_information";

  public final static String ADVERTSEMENT="_advertsement";

  public final static String COMMENTLIST="_comment_list";

  public final static String COMMENTDETAIL="_comment_detail";

  public final static String ZXFINANCE="_zxfinance";

  public final static String HTTP_RESPONE_INFORMATION=HTTP_RESPONSE+INFORMATION;

  public final static String HTTP_RESPONE_ADVERTSEMENT=HTTP_RESPONSE+ADVERTSEMENT;

  public final static String HTTP_RESPONE_COMMENTLIST=HTTP_RESPONSE+COMMENTLIST;

  public final static String HTTP_RESPONE_COMMENTDETAIL=HTTP_RESPONSE+COMMENTDETAIL;

  public final static String HTTP_RESPONE_ZXFINANCE=HTTP_RESPONSE+ZXFINANCE;


  public interface QuoteEventTag{
    /** 所有产品报价事件标签*/
    String REPLY_SYMBOL_ALLLIST = "REPLY_SYMBOL_ALLLIST";

    /** 产品更新事件标签*/
    String REPLY_SYMBOL_UPDATE = "REPLY_SYMBOL_UPDATE";

    /** 产品报价更新事件标签*/
    String REPLY_SYMBOL_TICK_UPDATE = "REPLY_SYMBOL_TICK_UPDATE";

  }

  /**交易服务器相关 tag */
  public interface TradeEventTag{
    /** 账户资金标签*/
    String REPLY_ACCOUNT_UPDATE = "REPLY_ACCOUNT_UPDATE";/** 持仓列表更新标签*/
    /** 持仓多条数据的更新标签*/
    String REPLY_POSITION_LIST = "REPLY_POSITION_LIST";
    /** 持仓单条数据的更新标签*/
    String REPLY_POSITION_UPDATE = "REPLY_POSITION_UPDATE";
    /** 执行平仓 */
    String REPLY_ORDER_CLOSE_START = "REPLY_ORDER_CLOSE_START";
    /** 平仓成功 */
    String REPLY_ORDER_CLOSE_SUCCESS = "REPLY_ORDER_CLOSE_SUCCESS";

  }


  /** 配置中心 通用标签*/
  public final static String REPLY_HTTP_NORMALREQ = "REPLY_HTTP_NORMALREQ";
  public final static String REPLY_HTTP_RGS = "REPLY_HTTP_RGS";

  public final static String REPLY_LOGIN_RESULT="REPLY_LOGIN_RESULT";

  /** 图表数据 **/
  public enum LINEDATA {
    REPLY_KLINEDATA_LIST("REPLY_KLINEDATA_LIST"),//k线图初始化请求成功
    REPLY_KLINEDATA_ADD_LIST("REPLY_KLINEDATA_ADD_LIST"),//k线图追加历史数据请求
    REPLY_KLINEDATA_NODATA("REPLY_KLINEDATA_NODATA"),//k线图无数据
    REPLY_KLINEDATA_NOLATEST("REPLY_KLINEDATA_NOLATEST"),//k线图最新数据
    REPLY_KLINEDATA_TIMEOUT("REPLY_KLINEDATA_TIMEOUT"),//k线图请求超时
    REPLY_KLINEDATA_KLINETICK_UPDATE("REPLY_KLINEDATA_KLINETICK_UPDATE"),//k线实时刷新数据

    REPLY_TIMELINEDATA_LIST("REPLY_TIMELINEDATA_LIST"),//分时图初始化请求成功
    REPLY_TIMELINEDATA_NODATA("REPLY_TIMELINEDATA_NODATA"),//分时图无数据
    REPLY_TIMELINEDATA_TIMEOUT("REPLY_TIMELINEDATA_TIMEOUT"),//分时图请求超时
    REPLY_TIMELINEDATA_UPTRENDTICK_UPDATE("REPLY_TIMELINEDATA_UPTRENDTICK_UPDATE");//分时图实时刷新数据

    private String tag;
     LINEDATA(String tag){
      this.tag=tag;
    }

    public String getTag() {
      return tag;
    }
  }

  /** 订单事件 **/
  public enum ORDER {
    REPLY_ORDER_FAILED("REPLY_ORDER_FAILED"),//下单失败
    REPLY_ORDER_SUCCESS("REPLY_ORDER_SUCCESS");//下单成功

    private String tag;

    ORDER(String tag){
      this.tag=tag;
    }
    public String  getTag(){
      return tag;
    }
  }

  /** 消息中心*/
  public final static String REPLY_MESSAGE_CENTER_LIST="REPLY_MESSAGE_CENTER_LIST";
  public final static String REPLY_MESSAGE_CENTER_ITEM="REPLY_MESSAGE_CENTER_ITEM";

  /** 网络连接状态 **/
  public enum NET{
    REPLY_NET_SUCCESS("REPLY_NET_SUCCESS"),
    REPLY_NET_FAILED("REPLY_NET_FAILED");

    private String tag;

    NET(String tag){
      this.tag=tag;
    }

    public String getTag() {
      return tag;
    }
  }

  /** 非交易时间段  **/
  public final static String REPLY_NO_TRADE_TIME="REPLY_NO_TRADE_TIME";

  /** 行情服务器连接通知 **/
  public final static String REPLY_LOGIN_TO_SERVICE="REPLY_LOGIN_TO_SERVICE";
}
