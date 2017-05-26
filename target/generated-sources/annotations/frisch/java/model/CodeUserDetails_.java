package frisch.java.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CodeUserDetails.class)
public abstract class CodeUserDetails_ {

	public static volatile SingularAttribute<CodeUserDetails, String> firstname;
	public static volatile SingularAttribute<CodeUserDetails, String> password;
	public static volatile SingularAttribute<CodeUserDetails, Role> role;
	public static volatile SingularAttribute<CodeUserDetails, Long> id;
	public static volatile SingularAttribute<CodeUserDetails, String> lastname;

}

