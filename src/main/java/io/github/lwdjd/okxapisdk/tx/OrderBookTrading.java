package io.github.lwdjd.okxapisdk.tx;

import io.github.lwdjd.okxapisdk.account.Account;


import java.util.Objects;

import static io.github.lwdjd.okxapisdk.getdata.Network.postWithSignature;
import static io.github.lwdjd.okxapisdk.getdata.PublicData.httpProxy;


/**
 * 撮合交易
 */
public class OrderBookTrading {
    //存储私有请求头信息



    /**
     * 请求参数
     * 参数名	类型	是否必须	描述
     * instId	String	是	产品ID，如 BTC-USDT
     * tdMode	String	是	交易模式
     * 保证金模式：isolated：逐仓 ；cross：全仓
     * 非保证金模式：cash：非保证金
     * spot_isolated：现货逐仓(仅适用于现货带单) ，现货带单时，tdMode 的值需要指定为spot_isolated
     * ccy	String	否	保证金币种，仅适用于单币种保证金模式下的全仓杠杆订单
     * clOrdId	String	否	客户自定义订单ID
     * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字且长度要在1-32位之间。
     * tag	String	否	订单标签
     * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字，且长度在1-16位之间。
     * side	String	是	订单方向
     * buy：买， sell：卖
     * posSide	String	可选	持仓方向
     * 在开平仓模式下必填，且仅可选择 long 或 short。 仅适用交割、永续。
     * ordType	String	是	订单类型
     * market：市价单
     * limit：限价单
     * post_only：只做maker单
     * fok：全部成交或立即取消
     * ioc：立即成交并取消剩余
     * optimal_limit_ioc：市价委托立即成交并取消剩余（仅适用交割、永续）
     * mmp：做市商保护(仅适用于组合保证金账户模式下的期权订单)
     * mmp_and_post_only：做市商保护且只做maker单(仅适用于组合保证金账户模式下的期权订单)
     * sz	String	是	委托数量
     * px	String	可选	委托价格，仅适用于limit、post_only、fok、ioc、mmp、mmp_and_post_only类型的订单
     * 期权下单时，px/pxUsd/pxVol 只能填一个
     * pxUsd	String	可选	以USD价格进行期权下单
     * 仅适用于期权
     * 期权下单时 px/pxUsd/pxVol 必填一个，且只能填一个
     * pxVol	String	可选	以隐含波动率进行期权下单，例如 1 代表 100%
     * 仅适用于期权
     * 期权下单时 px/pxUsd/pxVol 必填一个，且只能填一个
     * reduceOnly	Boolean	否	是否只减仓，true 或 false，默认false
     * 仅适用于币币杠杆，以及买卖模式下的交割/永续
     * 仅适用于单币种保证金模式和跨币种保证金模式
     * tgtCcy	String	否	市价单委托数量sz的单位，仅适用于币币市价订单
     * base_ccy: 交易货币 ；quote_ccy：计价货币
     * 买单默认quote_ccy， 卖单默认base_ccy
     * banAmend	Boolean	否	是否禁止币币市价改单，true 或 false，默认false
     * 为true时，余额不足时，系统不会改单，下单会失败，仅适用于币币市价单
     * quickMgnType	String	否	一键借币类型，仅适用于杠杆逐仓的一键借币模式：
     * manual：手动，auto_borrow：自动借币，auto_repay：自动还币
     * 默认是manual：手动（已弃用）
     * stpId	String	否	自成交保护ID。来自同一个母账户配着同一个ID的订单不能自成交
     * 用户自定义1<=x<=999999999的整数（已弃用）
     * stpMode	String	否	自成交保护模式
     * 默认为 cancel maker
     * cancel_maker,cancel_taker, cancel_both
     * Cancel both不支持FOK
     * attachAlgoOrds	Array of object	否	下单附带止盈止损信息
     * > attachAlgoClOrdId	String	否	下单附带止盈止损时，客户自定义的策略订单ID
     * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字且长度要在1-32位之间。
     * 订单完全成交，下止盈止损委托单时，该值会传给algoClOrdId
     * > tpTriggerPx	String	可选	止盈触发价
     * 对于条件止盈单，如果填写此参数，必须填写 止盈委托价
     * > tpOrdPx	String	可选	止盈委托价
     * 对于条件止盈单，如果填写此参数，必须填写 止盈触发价
     * 对于限价止盈单，需填写此参数，不需要填写止盈触发价
     * > tpOrdKind	String	否	止盈订单类型
     * condition: 条件单
     * limit: 限价单
     * 默认为condition
     * > slTriggerPx	String	可选	止损触发价，如果填写此参数，必须填写 止损委托价
     * > slOrdPx	String	可选	止损委托价，如果填写此参数，必须填写 止损触发价
     * 委托价格为-1时，执行市价止损
     * > tpTriggerPxType	String	否	止盈触发价类型
     * last：最新价格
     * index：指数价格
     * mark：标记价格
     * 默认为last
     * > slTriggerPxType	String	否	止损触发价类型
     * last：最新价格
     * index：指数价格
     * mark：标记价格
     * 默认为last
     * > sz	String	可选	数量。仅适用于“多笔止盈”的止盈订单，且对于“多笔止盈”的止盈订单必填
     * > amendPxOnTriggerType	String	否	是否启用开仓价止损，仅适用于分批止盈的止损订单，第一笔止盈触发时，止损触发价格是否移动到开仓均价止损
     * 0：不开启，默认值
     * 1：开启，且止损触发价不能为空
     *
     * @param body POST内容主体
     * @return 响应结果
     */
    public static String order(String body, Account account){
        String requestPath = "/api/v5/trade/order";
        String str = null;
        try{
            if(!Objects.equals(httpProxy, "")){
                str=postWithSignature(requestPath, body , account , httpProxy);
            }
            else {
                str=postWithSignature(requestPath, body , account);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据获取错误！！！");// 处理异常;
        }
        return str;
    }

    /**
     * 请求参数
     * 参数名	类型	    是否必须	描述
     * instId	String	是	    产品ID，如 BTC-USDT
     * ordId	String	可选	    订单ID， ordId和clOrdId必须传一个，若传两个，以ordId为主
     * clOrdId	String	可选    	用户自定义ID
     *
     * @param body 交易参数
     * @param account 交易账户
     * @return 返回数据
     */
    public static String cancelOrder(String body, Account account){
        String requestPath = "/api/v5/trade/cancel-order";
        String str = null;
        try{
            if(!Objects.equals(httpProxy, "")){
                str=postWithSignature(requestPath, body , account , httpProxy);
            }
            else {
                str=postWithSignature(requestPath, body , account);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据获取错误！！！");// 处理异常;
        }
        return str;
    }

}

