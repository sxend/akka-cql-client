package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public enum Consistency {
    ANY(0x0000),
    ONE(0x0001),
    TWO(0x0002),
    THREE(0x0003),
    QUORUM(0x0004),
    ALL(0x0005),
    LOCAL_QUORUM(0x0006),
    EACH_QUORUM(0x0007),
    SERIAL(0x0008),
    LOCAL_SERIAL(0x0009),
    LOCAL_ONE(0x000A),;
    public final short level;

    Consistency(int level) {
        this.level = (short) level;
    }
    public Consistency valueOf(short level){
        for(Consistency c:values()){
            if(c.level == level) return c;
        }
        throw new RuntimeException("invalid level");
    }
}
