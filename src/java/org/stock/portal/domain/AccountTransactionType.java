package org.stock.portal.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account_transaction_types database table.
 * 
 */
@Entity
@Table(name="account_transaction_types")
public class AccountTransactionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACCOUNT_TRANSACTION_TYPES_ID_GENERATOR", sequenceName="account_transaction_types_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACCOUNT_TRANSACTION_TYPES_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private long id;

	@Column(length=255)
	private String name;

    public AccountTransactionType() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}