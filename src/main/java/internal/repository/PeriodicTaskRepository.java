package internal.repository;

import internal.model.node.Node;
import internal.scheduler.task.PeriodicTask;

import java.util.List;

public interface PeriodicTaskRepository {

    List<PeriodicTask> findAll();

    void deleteAll();

    void saveTask(Node node);

    Node findTaskById(Integer taskId);

    long updateTask(Node node);

    void deleteNodeById(Integer nodeId);
}
