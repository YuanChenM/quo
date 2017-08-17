/**
 * SendEmailService.
 * 
 * @screen common
 * 
 */
package com.quotation.common.service;

import com.quotation.common.entity.TnmMailConfig;
import com.quotation.core.base.BaseService;
import com.quotation.core.bean.BaseParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SendEmailService.
 */
@Service
public class SendEmailService extends BaseService {

    /**
     * 
     * The Constructors Method.
     *
     */
    public SendEmailService() {}

    /**
     * Get MailSendFlag.<br>
     * 
     * @return true: mailSendFlag = 1
     */
    public boolean getMailSendFlag() {
        BaseParam param = new BaseParam();
        List<TnmMailConfig> list = baseMapper.select(this.getSqlId("getMailSendFlag"), param);
        if (list != null && list.size() > 0) {
            Integer mailSendFlag = list.get(0).getMailSendFlag();
            if (mailSendFlag != null && mailSendFlag.intValue() == 1) {
                return true;
            }
        }
        return false;
    }
}
