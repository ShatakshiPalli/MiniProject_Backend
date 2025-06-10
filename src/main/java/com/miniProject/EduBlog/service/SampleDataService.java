package com.miniProject.EduBlog.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.repository.PostRepository;
import com.miniProject.EduBlog.repository.UserRepository;

@Service
public class SampleDataService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public SampleDataService(UserRepository userRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        postRepository.deleteAll();
        userRepository.deleteAll();

        // Create sample users with realistic names
        User davidChen = createUser("davidchen", "david.chen@email.com", "password123");
        User sarahPatel = createUser("sarahpatel", "sarah.patel@email.com", "password123");
        User michaelBrown = createUser("mbrown", "michael.brown@email.com", "password123");
        User emilyClarke = createUser("emilyclarke", "emily.clarke@email.com", "password123");
        User jamesWilson = createUser("jwilson", "james.wilson@email.com", "password123");

        // Create Mathematics Posts
        createPost(davidChen, "The Fascinating World of Calculus: From Basic Concepts to Real-world Applications",
            "A comprehensive guide to understanding calculus and its practical applications in various fields",
            "Calculus, often described as the mathematics of change, stands as one of the most powerful tools in modern mathematics. In this comprehensive exploration, we'll delve deep into both the theoretical foundations and practical applications that make calculus indispensable in our world today.\n\n" +
            "Understanding the Fundamentals:\n" +
            "At its core, calculus deals with two fundamental concepts: differentiation and integration. Differentiation helps us understand rates of change - how one quantity varies with respect to another. Integration, conversely, allows us to find total quantities when we know rates of change. These concepts might sound abstract, but they're deeply rooted in real-world phenomena.\n\n" +
            "Real-world Applications:\n" +
            "1. Physics and Engineering:\n" +
            "   - Calculating velocity and acceleration from position data\n" +
            "   - Analyzing electrical circuits and electromagnetic fields\n" +
            "   - Designing optimal shapes for aircraft and buildings\n\n" +
            "2. Economics and Finance:\n" +
            "   - Optimizing profit functions\n" +
            "   - Analyzing market trends and growth rates\n" +
            "   - Calculating compound interest and investment returns\n\n" +
            "3. Biology and Medicine:\n" +
            "   - Modeling population growth\n" +
            "   - Analyzing blood flow through vessels\n" +
            "   - Calculating drug absorption rates\n\n" +
            "Historical Context:\n" +
            "The development of calculus in the 17th century by Newton and Leibniz marked a revolutionary moment in mathematical history. Their work provided tools to solve problems that had puzzled mathematicians for centuries, from calculating areas of curved shapes to understanding planetary motion.\n\n" +
            "Modern Developments:\n" +
            "Today, calculus continues to evolve and find new applications. Computer graphics use calculus for smooth animations. Machine learning algorithms employ calculus for optimization. Even social media algorithms use calculus-based methods to predict user preferences and behavior.\n\n" +
            "Practical Examples:\n" +
            "Let's consider a specific example: roller coaster design. Engineers use calculus to:\n" +
            "- Calculate the optimal curve for maximum excitement but safe g-forces\n" +
            "- Determine the right height and speed relationships\n" +
            "- Ensure smooth transitions between different sections\n\n" +
            "This practical application demonstrates how calculus transforms abstract mathematical concepts into real-world solutions.\n\n" +
            "Conclusion:\n" +
            "Whether you're a student beginning your mathematical journey or a professional seeking deeper understanding, calculus offers powerful tools for analyzing and solving complex problems. Its applications span virtually every field of science and engineering, making it an essential subject for anyone interested in understanding how our world works at a fundamental level.",
            "MATHEMATICS");

        // Create Science Posts
        createPost(sarahPatel, "Quantum Computing: The Next Technological Revolution",
            "An in-depth exploration of quantum computing principles and their potential impact on technology and society",
            "Quantum computing represents one of the most significant technological leaps in human history, promising to revolutionize everything from cryptography to drug discovery. This article explores the fundamental principles of quantum computing and its far-reaching implications for our future.\n\n" +
            "Fundamental Principles:\n" +
            "Unlike classical computers that use bits (0s and 1s), quantum computers utilize quantum bits or 'qubits.' These qubits can exist in multiple states simultaneously through a phenomenon called superposition. Additionally, qubits can be 'entangled,' allowing changes to one qubit to instantly affect another, regardless of the distance between them.\n\n" +
            "Key Quantum Computing Concepts:\n\n" +
            "1. Superposition:\n" +
            "The ability of a quantum system to exist in multiple states simultaneously is perhaps the most counterintuitive aspect of quantum mechanics. In practical terms, this means a quantum computer with n qubits can represent 2^n states simultaneously, leading to exponential computational power.\n\n" +
            "2. Quantum Entanglement:\n" +
            "Einstein famously called this 'spooky action at a distance.' When qubits become entangled, the state of one is directly correlated with the state of another. This property allows quantum computers to perform complex calculations that would be impossible for classical computers.\n\n" +
            "3. Quantum Decoherence:\n" +
            "One of the biggest challenges in quantum computing is maintaining quantum states, as they are extremely sensitive to environmental interference. Scientists must keep qubits at near absolute zero temperatures and isolated from external influences.\n\n" +
            "Practical Applications:\n\n" +
            "1. Cryptography and Security:\n" +
            "- Breaking current encryption methods\n" +
            "- Developing quantum-safe cryptography\n" +
            "- Secure communication through quantum key distribution\n\n" +
            "2. Drug Discovery and Medical Research:\n" +
            "- Simulating molecular interactions\n" +
            "- Protein folding analysis\n" +
            "- Personalized medicine development\n\n" +
            "3. Climate Modeling and Environmental Science:\n" +
            "- Complex weather pattern analysis\n" +
            "- Climate change prediction models\n" +
            "- Optimization of renewable energy systems\n\n" +
            "Current State of Technology:\n" +
            "Major tech companies like IBM, Google, and Intel are racing to develop practical quantum computers. While current quantum computers are still in their infancy, they've already achieved 'quantum supremacy' - performing calculations that would be impossible for classical computers.\n\n" +
            "Future Implications:\n" +
            "The development of practical quantum computers could:\n" +
            "- Transform financial modeling and optimization\n" +
            "- Revolutionize artificial intelligence and machine learning\n" +
            "- Enable breakthroughs in materials science\n" +
            "- Solve complex logistics and supply chain problems\n\n" +
            "Challenges and Limitations:\n" +
            "Despite the enormous potential, significant challenges remain:\n" +
            "- Maintaining quantum coherence\n" +
            "- Scaling up the number of qubits\n" +
            "- Developing quantum error correction\n" +
            "- Creating practical quantum programming languages\n\n" +
            "Conclusion:\n" +
            "Quantum computing stands at the frontier of technological innovation, promising to solve problems that are currently intractable. While significant challenges remain, the potential benefits make this one of the most exciting fields in modern science and technology.",
            "SCIENCE");

        // Create Programming Posts
        createPost(michaelBrown, "The Evolution of Artificial Intelligence: From Neural Networks to Deep Learning",
            "A comprehensive exploration of AI development and its impact on modern technology",
            "Artificial Intelligence has transformed from a sci-fi concept to a reality that impacts our daily lives. This article explores the journey of AI technology, its current state, and future prospects.\n\n" +
            "Historical Development:\n" +
            "The concept of artificial intelligence emerged in the 1950s, but the real breakthrough came with the development of neural networks. These systems, inspired by the human brain, laid the foundation for modern AI.\n\n" +
            "Key Components of Modern AI:\n\n" +
            "1. Machine Learning Fundamentals:\n" +
            "- Supervised Learning: Training models with labeled data\n" +
            "- Unsupervised Learning: Finding patterns in unlabeled data\n" +
            "- Reinforcement Learning: Learning through trial and error\n\n" +
            "2. Deep Learning Architecture:\n" +
            "- Convolutional Neural Networks (CNNs) for image processing\n" +
            "- Recurrent Neural Networks (RNNs) for sequential data\n" +
            "- Transformer models for natural language processing\n\n" +
            "3. Training and Optimization:\n" +
            "- Backpropagation algorithms\n" +
            "- Gradient descent optimization\n" +
            "- Hyperparameter tuning\n\n" +
            "Real-world Applications:\n" +
            "1. Healthcare:\n" +
            "- Disease diagnosis through image analysis\n" +
            "- Drug discovery and development\n" +
            "- Personalized treatment recommendations\n\n" +
            "2. Finance:\n" +
            "- Algorithmic trading\n" +
            "- Fraud detection\n" +
            "- Risk assessment\n\n" +
            "3. Transportation:\n" +
            "- Autonomous vehicles\n" +
            "- Traffic prediction\n" +
            "- Route optimization\n\n" +
            "Ethical Considerations:\n" +
            "The rise of AI brings important ethical questions:\n" +
            "- Bias in AI systems\n" +
            "- Privacy concerns\n" +
            "- Impact on employment\n" +
            "- AI safety and control\n\n" +
            "Future Directions:\n" +
            "The field continues to evolve with developments in:\n" +
            "- Quantum AI\n" +
            "- Explainable AI\n" +
            "- General AI research\n" +
            "- Edge computing and AI\n\n" +
            "Conclusion:\n" +
            "AI technology stands at the forefront of innovation, promising to revolutionize every aspect of our lives. Understanding its principles, capabilities, and limitations is crucial for anyone interested in technology and its future impact on society.",
            "PROGRAMMING");

        // Create History Posts
        createPost(emilyClarke, "The Renaissance: A Cultural Revolution That Shaped Modern Europe",
            "An in-depth analysis of the Renaissance period and its lasting impact on art, science, and society",
            "The Renaissance, spanning roughly from the 14th to the 17th century, marked a transformative period in European history that bridged the Middle Ages to modern times. This comprehensive exploration examines the factors that sparked this cultural revolution and its enduring influence.\n\n" +
            "Origins and Context:\n" +
            "The Renaissance emerged in Florence, Italy, driven by:\n" +
            "- Increased urbanization and wealth\n" +
            "- Rediscovery of classical texts\n" +
            "- Rise of powerful banking families\n" +
            "- Growing merchant class\n\n" +
            "Key Aspects of the Renaissance:\n\n" +
            "1. Art and Architecture:\n" +
            "- Development of perspective in painting\n" +
            "- Realistic portraiture and anatomical accuracy\n" +
            "- Innovation in architectural design\n" +
            "- Famous works by da Vinci, Michelangelo, and Raphael\n\n" +
            "2. Scientific Revolution:\n" +
            "- Copernican heliocentrism\n" +
            "- Anatomical studies by Vesalius\n" +
            "- Galileo's astronomical observations\n" +
            "- Development of the scientific method\n\n" +
            "3. Humanism and Philosophy:\n" +
            "- Focus on human potential and achievement\n" +
            "- Revival of classical learning\n" +
            "- Emphasis on individual worth\n" +
            "- Secular approach to knowledge\n\n" +
            "Social and Political Impact:\n" +
            "The Renaissance transformed society through:\n" +
            "- Rise of secular education\n" +
            "- Growth of universities\n" +
            "- Development of printing and literacy\n" +
            "- Changes in political structures\n\n" +
            "Cultural Achievements:\n" +
            "1. Literature:\n" +
            "- Works of Dante and Petrarch\n" +
            "- Development of vernacular literature\n" +
            "- Shakespeare and the English Renaissance\n\n" +
            "2. Music:\n" +
            "- Polyphonic composition\n" +
            "- Secular music development\n" +
            "- New musical instruments\n\n" +
            "Legacy and Modern Relevance:\n" +
            "The Renaissance's influence continues through:\n" +
            "- Modern scientific inquiry\n" +
            "- Artistic techniques and appreciation\n" +
            "- Educational principles\n" +
            "- Humanistic values\n\n" +
            "Conclusion:\n" +
            "The Renaissance represents a crucial turning point in human history, establishing principles and values that continue to shape our modern world. Its emphasis on human potential, rational inquiry, and artistic excellence remains relevant today.",
            "HISTORY");

        // Create Literature Posts
        createPost(jamesWilson, "Modern Poetry: Breaking Conventions and Finding New Voice",
            "Exploring the evolution and impact of modern poetry in contemporary literature",
            "Modern poetry represents a dramatic departure from traditional forms, embracing free verse, experimental techniques, and diverse voices. This article examines the transformation of poetry in the modern era and its significance in contemporary literature.\n\n" +
            "Historical Context:\n" +
            "The modernist movement in poetry emerged in the early 20th century as a response to:\n" +
            "- Rapid technological change\n" +
            "- Social upheaval and world wars\n" +
            "- New psychological theories\n" +
            "- Cultural fragmentation\n\n" +
            "Key Characteristics:\n\n" +
            "1. Form and Structure:\n" +
            "- Abandonment of traditional meters\n" +
            "- Experimentation with line breaks\n" +
            "- Use of white space\n" +
            "- Visual poetry and concrete forms\n\n" +
            "2. Language and Imagery:\n" +
            "- Complex metaphors and symbolism\n" +
            "- Stream of consciousness\n" +
            "- Fragmentation and juxtaposition\n" +
            "- Multiple perspectives\n\n" +
            "3. Themes and Subjects:\n" +
            "- Urban life and alienation\n" +
            "- Personal and collective identity\n" +
            "- Political and social justice\n" +
            "- Environmental concerns\n\n" +
            "Influential Movements:\n" +
            "1. Imagism:\n" +
            "- Focus on precise imagery\n" +
            "- Economy of language\n" +
            "- Direct treatment of the subject\n\n" +
            "2. Beat Poetry:\n" +
            "- Spontaneous expression\n" +
            "- Cultural criticism\n" +
            "- Oral performance\n\n" +
            "3. Confessional Poetry:\n" +
            "- Personal experience\n" +
            "- Psychological depth\n" +
            "- Emotional intensity\n\n" +
            "Contemporary Trends:\n" +
            "Modern poetry continues to evolve through:\n" +
            "- Digital and multimedia poetry\n" +
            "- Spoken word and performance\n" +
            "- Cross-cultural influences\n" +
            "- Social media platforms\n\n" +
            "Impact on Literature:\n" +
            "Modern poetry has influenced:\n" +
            "- Narrative techniques\n" +
            "- Literary criticism\n" +
            "- Creative writing education\n" +
            "- Popular culture\n\n" +
            "Conclusion:\n" +
            "Modern poetry represents a vital and evolving art form that continues to push boundaries and explore new ways of expressing human experience. Its influence extends beyond literature into various aspects of contemporary culture and communication.",
            "LITERATURE");

        // Continue with other posts in similar detailed format...
        // [Additional posts would follow the same pattern with 200-300+ words each]
    }

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    private void createPost(User author, String title, String description, String content, String category) {
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setContent(content);
        post.setCategory(category);
        post.setAuthor(author);
        post.onCreate();
        postRepository.save(post);
    }
} 
