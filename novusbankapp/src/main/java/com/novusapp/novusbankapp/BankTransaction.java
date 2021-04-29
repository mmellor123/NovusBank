package com.novusapp.novusbankapp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class BankTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;
    
    @Column(name = "amount")
    private int amount;
    
    @Column(name = "date")
    private Date date;

    private int receiverBalance;
    private int senderBalance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getReceiverBalance() {
		return receiverBalance;
	}

	public void setReceiverBalance(int balance) {
		this.receiverBalance = balance;
	}

	public int getSenderBalance() {
		return senderBalance;
	}

	public void setSenderBalance(int senderBalance) {
		this.senderBalance = senderBalance;
	}
}
