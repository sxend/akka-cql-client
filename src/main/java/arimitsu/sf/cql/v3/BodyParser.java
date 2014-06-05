package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 2014/06/05.
 */
public interface BodyParser<R> {
    public R parse(byte[] body);

    public byte[] parse(R r);
}
