package domainapp.modules.simple.dom.planta;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QPlanta extends PersistableExpressionImpl<Planta> implements PersistableExpression<Planta>
{
    public static final QPlanta jdoCandidate = candidate("this");

    public static QPlanta candidate(String name)
    {
        return new QPlanta(null, name, 5);
    }

    public static QPlanta candidate()
    {
        return jdoCandidate;
    }

    public static QPlanta parameter(String name)
    {
        return new QPlanta(Planta.class, name, ExpressionType.PARAMETER);
    }

    public static QPlanta variable(String name)
    {
        return new QPlanta(Planta.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression nombre;
    public final StringExpression provincia;
    public final StringExpression cliente;
    public final BooleanExpression activo;
    public final CollectionExpression equipos;

    public QPlanta(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.nombre = new StringExpressionImpl(this, "nombre");
        this.provincia = new StringExpressionImpl(this, "provincia");
        this.cliente = new StringExpressionImpl(this, "cliente");
        this.activo = new BooleanExpressionImpl(this, "activo");
        this.equipos = new CollectionExpressionImpl(this, "equipos");
    }

    public QPlanta(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.nombre = new StringExpressionImpl(this, "nombre");
        this.provincia = new StringExpressionImpl(this, "provincia");
        this.cliente = new StringExpressionImpl(this, "cliente");
        this.activo = new BooleanExpressionImpl(this, "activo");
        this.equipos = new CollectionExpressionImpl(this, "equipos");
    }
}
