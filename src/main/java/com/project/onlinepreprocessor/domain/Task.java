package com.project.onlinepreprocessor.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Null;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázqez
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Task
{
    /**
     * The id of the task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The dataset associated to this task
     */
    @OneToOne(mappedBy = "task")
    private Dataset dataset;

    /**
     * The state of the task
     */
    private String state;

    /**
     * The message of the task when failed
     * 
     */
    @Column(nullable = true)
    private String message;

    /**
     * Creates an instance of the task
     * @param dataset the dataset associated to the task
     * @param state the state of the task
     * @param message the message of the task when failed
     */
    public Task(Dataset dataset, String state, String message)
    {
        this.dataset = dataset;
        this.state = state;
        this.message = message;
    }

    /**
     * The default constructor of the task
     */
    public Task()
    {

    }

    /**
     * Return the dataset associated to the task
     * @return the dataset associated to the task
     */
    public Dataset getDataset()
    {
        return this.dataset;
    }

    /**
     * Stablish the dataset associated to the task
     * @param dataset the dataset associated to the task
     */
    public void setDataset(Dataset dataset)
    {
        this.dataset = dataset;
    }

    /**
     * Return the id of the task
     * @return the id of the task
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Stablish the id of the task
     * @param id the id of the task
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Return the state of the task
     * @return the state of the task
     */
    public String getState()
    {
        return this.state;
    }

    /**
     * Stablish the state of the task
     * @param state the state of the task
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Return the message of the task when failed
     * @return the message of the task when failed
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Stablish the message of the task when failed 
     * @param message the message of the task when failed
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}