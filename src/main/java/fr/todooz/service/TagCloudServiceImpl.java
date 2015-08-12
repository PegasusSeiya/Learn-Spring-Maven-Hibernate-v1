package fr.todooz.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.todooz.domain.Task;
import fr.todooz.util.TagCloud;

@Service
public class TagCloudServiceImpl implements TagCloudService {
    @Inject
    private SessionFactory sessionFactory;

    @Inject
    @Qualifier( "hibernate" )
    private TaskService    taskService;

    /**
     * Retrieve all tasks in base. Extract all their tags and keep only single
     * occurences. Return the TagCloud without doublon.
     */
    @Override
    @Transactional( readOnly = true )
    public TagCloud buildTagCloud() {
        TagCloud tagCloud = new TagCloud();

        List<Task> tasks = taskService.findAll(); 

        List<String> tags = new ArrayList<String>();

        for ( Task task : tasks ) {
            tags.addAll( Arrays.asList( task.getTagArray() ) );
        }
        List<String> tagsSansDoublon = tagCloud.newListSansDoublon( tags );

        tagCloud.setTags( tagsSansDoublon );

        return tagCloud;
    }
}
