package TourData.backend.domain.post.temp;

import TourData.backend.domain.post.model.Post;
import TourData.backend.domain.post.repository.PostRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDataInitializer implements CommandLineRunner {

    private final PostRepository postRepository;

    private final String imagePath = "https://images.unsplash.com/photo-1621044332832-717d5d986ab7?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

    @Override
    public void run(String... args) {
        postRepository.save(new Post( "location1", imagePath));
        postRepository.save(new Post( "location2", imagePath));
        postRepository.save(new Post( "location3", imagePath));
        postRepository.save(new Post( "location4", imagePath));
        postRepository.save(new Post( "location5", imagePath));
        postRepository.save(new Post( "location6", imagePath));
        postRepository.save(new Post( "location7", imagePath));
        postRepository.save(new Post( "location8", imagePath));
    }

    @PreDestroy
    public void cleanup() {
        postRepository.deleteAll();
    }

}