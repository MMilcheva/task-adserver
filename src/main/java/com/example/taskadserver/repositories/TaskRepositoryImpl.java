package com.example.taskadserver.repositories;

import com.example.taskadserver.models.Task;
import com.example.taskadserver.models.TaskFilterOptions;
import com.example.taskadserver.models.enums.TaskStatus;
import com.example.taskadserver.repositories.contracts.TaskRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepositoryImpl extends AbstractCRUDRepository<Task> implements TaskRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepositoryImpl(SessionFactory sessionFactory) {
        super(Task.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Task> getAllTasksFilter(TaskFilterOptions taskFilterOptions) {
        return filter(
                taskFilterOptions.getUserId(),
                taskFilterOptions.getTitle(),
                taskFilterOptions.getTaskDescription(),
                taskFilterOptions.getTaskStatus(),
                taskFilterOptions.getSortBy(),
                taskFilterOptions.getSortOrder());
    }

    @Override
    public List<Task> filter(
            Optional<Long> userId,
            Optional<String> title,
            Optional<String> taskDescription,
            Optional<TaskStatus> status,
            Optional<String> sortBy,
            Optional<String> sortOrder) {

        try (Session session = sessionFactory.openSession()) {
            StringBuilder queryString = new StringBuilder(" Select t from Task t ");
            ArrayList<String> filters = new ArrayList<>();
            Map<String, Object> queryParams = new HashMap<>();

            userId.ifPresent(value -> {
                filters.add(" t.user.userId = :userId ");
                queryParams.put("userId", value);
            });
            title.ifPresent(value -> {
                filters.add(" t.title like :title ");
                queryParams.put("title", "%" + value + "%");
            });

            taskDescription.ifPresent(value -> {
                filters.add(" t.taskDescription like :taskDescription ");
                queryParams.put("taskDescription", "%" + value + "%");
            });

            status.ifPresent(value -> {
                filters.add(" t.status = :status ");
                queryParams.put("status", value);
            });
            if (!filters.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filters));
            }

            if (sortBy.isPresent() && !sortBy.get().isEmpty()) {
                String sortOrderString = sortOrder.orElse("ASC");

                queryString.append(" ORDER BY t.").append(sortBy.get()).append(" ").append(sortOrderString);
            }
            Query<Task> queryList = session.createQuery(queryString.toString(), Task.class);
            queryList.setProperties(queryParams);

            return queryList.list();
        }
    }
}
