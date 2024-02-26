package xyz.ldqc.buka.data.repository.core.engine.query.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import xyz.ldqc.buka.data.repository.core.engine.query.BoolTypeEnum;
import xyz.ldqc.buka.data.repository.core.engine.query.Conditional;
import xyz.ldqc.buka.data.repository.core.engine.query.MultipleConditional;

/**
 * @author Fetters
 */
public class AndConditional implements MultipleConditional {

    private final List<Conditional> conditionals;

    public AndConditional(Conditional... conditionals) {
        this.conditionals = new LinkedList<>();
        this.conditionals.addAll(
            Arrays.stream(conditionals).filter(Objects::nonNull).collect(
                Collectors.toList()));
    }

    @Override
    public boolean judge(Object obj) {
        for (Conditional conditional : conditionals) {
            boolean judge = conditional.judge(obj);
            if (!judge) {
                return false;
            }
        }
        return true;
    }

    public Class<?> getTargetType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0) {
                Type typeArgument = typeArguments[0];
                if (typeArgument instanceof Class) {
                    return (Class<?>) typeArgument;
                }
            }
        }
        return null;
    }

    @Override
    public List<Conditional> getConditionals() {
        return this.conditionals;
    }

    @Override
    public void addConditional(Conditional conditional, BoolTypeEnum type) {
        if (conditional != null) {
            this.conditionals.add(conditional);
        }
    }
}
