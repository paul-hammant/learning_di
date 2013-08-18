package learning.di;

public abstract class ApplicationInjection<T> {

    protected final T instance;

    public ApplicationInjection(T instance) {
        this.instance = instance;
    }

    public abstract void unInject();
}
