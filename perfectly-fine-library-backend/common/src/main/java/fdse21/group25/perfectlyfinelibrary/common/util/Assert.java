package fdse21.group25.perfectlyfinelibrary.common.util;

import java.util.function.Supplier;

public class Assert {
    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> exceptionSupplier)
            throws X {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void isNull(Object object, Supplier<? extends X> exceptionSupplier) throws X {
        if (object != null) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void notNull(Object object, Supplier<? extends X> exceptionSupplier) throws X {
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }
}
