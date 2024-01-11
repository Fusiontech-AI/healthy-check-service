package org.fxkc.peis.exception;

import org.fxkc.common.core.exception.base.BaseException;

import java.io.Serial;

/**
 * 业务异常类
 *
 * @author Lion Li
 */
public class PeisException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PeisException(String code, Object... args) {
        super("peis", code, args, null);
    }
}
