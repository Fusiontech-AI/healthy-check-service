package org.fxkc.peis.register.insert;

import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.bo.TjRegisterAddBo;

import java.util.List;

/**
 * 体检登记接口
 */
public interface RegisterInsertService {

    /**
     * 插入登记信息
     * @return 登记主键id
     */
    public List<TjRegister> RegisterInsert(List<TjRegisterAddBo> tjRegisterAddBos);

    /**
     * 登记前置检查
     */
    public void RegisterPreCheck(List<TjRegisterAddBo> tjRegisterAddBos);
}
