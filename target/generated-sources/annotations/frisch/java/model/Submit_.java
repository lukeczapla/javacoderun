package frisch.java.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Submit.class)
public abstract class Submit_ {

	public static volatile SingularAttribute<Submit, Date> date;
	public static volatile SingularAttribute<Submit, Long> score;
	public static volatile SingularAttribute<Submit, String> className;
	public static volatile SingularAttribute<Submit, Long> id;
	public static volatile SingularAttribute<Submit, String> text;
	public static volatile SingularAttribute<Submit, Boolean> passed;
	public static volatile SingularAttribute<Submit, Long> userId;
	public static volatile SingularAttribute<Submit, Long> assignmentId;

}

