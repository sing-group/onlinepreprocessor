package org.strep.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * JPA Bean for the Dataset objects managed by application
 * @author Ismael Vázquez
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class TaskCreateSdataset extends Task
{
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an instance of TaskCreateSdataset
     * 
     * @param dataset the dataset associated to the task
     * @param state   the state of the task
     * @param message the message of the task when failed
     */
    public TaskCreateSdataset(Dataset dataset, String state, String message)
    {
        super(dataset, state, message);
    }

    /**
     * The default constructor
     */
    public TaskCreateSdataset()
    {
        super();
    }
}