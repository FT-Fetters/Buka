package xyz.ldqc.buka.data.repository.core.engine.query.support;

import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;

/**
 * @author Fetters
 */
public class TrueOnlyConditional implements Conditional {

    @Override
    public boolean judge(Object obj) {
        return true;
    }
}
