package TourData.backend.domain.post.service;

import TourData.backend.domain.post.dto.PostDto.PostResponse;
import TourData.backend.domain.post.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponse(post.getThumbNail(), post.getLocation()))
                .collect(Collectors.toList());
    }

}
