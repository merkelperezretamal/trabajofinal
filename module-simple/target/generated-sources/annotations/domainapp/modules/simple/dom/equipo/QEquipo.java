package domainapp.modules.simple.dom.equipo;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QEquipo extends PersistableExpressionImpl<Equipo> implements PersistableExpression<Equipo>
{
    public static final QEquipo jdoCandidate = candidate("this");

    public static QEquipo candidate(String name)
    {
        return new QEquipo(null, name, 5);
    }

    public static QEquipo candidate()
    {
        return jdoCandidate;
    }

    public static QEquipo parameter(String name)
    {
        return new QEquipo(Equipo.class, name, ExpressionType.PARAMETER);
    }

    public static QEquipo variable(String name)
    {
        return new QEquipo(Equipo.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression denominacion;
    public final NumericExpression<Double> horometro;
    public final NumericExpression<Double> porcentajeDisponibilidad;
    public final NumericExpression<Double> rpm;
    public final NumericExpression<Double> presionAceite;
    public final StringExpression notes;

    public QEquipo(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.denominacion = new StringExpressionImpl(this, "denominacion");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.porcentajeDisponibilidad = new NumericExpressionImpl<Double>(this, "porcentajeDisponibilidad");
        this.rpm = new NumericExpressionImpl<Double>(this, "rpm");
        this.presionAceite = new NumericExpressionImpl<Double>(this, "presionAceite");
        this.notes = new StringExpressionImpl(this, "notes");
    }

    public QEquipo(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.denominacion = new StringExpressionImpl(this, "denominacion");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.porcentajeDisponibilidad = new NumericExpressionImpl<Double>(this, "porcentajeDisponibilidad");
        this.rpm = new NumericExpressionImpl<Double>(this, "rpm");
        this.presionAceite = new NumericExpressionImpl<Double>(this, "presionAceite");
        this.notes = new StringExpressionImpl(this, "notes");
    }
}
