//Java Graphics Society - MIT
package opengles;

/**
 *
 * @author gbarbieri
 */
public class Semantic {

    protected static final Semantic instance = new Semantic();

    /**
     * @return the instance
     */
    public static Semantic getInstance() {
        return instance;
    }

    public class Attr {

        public final int POSITION = 0;
        public final int NORMAL = 2;
        public final int COLOR = 1;
        public final int TEX_COORD = 5;
        public final int CAMERA_SPHERE_POS = 6;
        public final int SPHERE_RADIUS = 7;
    }

    public class Buffer {

        public final int STATIC = 0;
        public final int DYNAMIC = 1;
    }

    public class Frag {

        public final int COLOR = 0;
        public final int RED = 0;
        public final int GREEN = 1;
        public final int BLUE = 2;
        public final int ALPHA = 0;
    }

    public class Image {

        public final int DIFFUSE = 0;
        public final int PICKING = 1;
    }

    public class Object {

        public final int VAO = 0;
        public final int VBO = 1;
        public final int IBO = 2;
        public final int TEXTURE = 3;
        public final int SAMPLER = 4;
        public final int SIZE = 5;
    }

    public class Renderbuffer {

        public final int DEPTH = 0;
        public final int COLOR0 = 1;
    }

    public class Sampler {

        public final int DIFFUSE = 0;
        public final int POSITION = 4;
        public final int TEXCOORD = 5;
        public final int COLOR = 6;
        public final int GAUSSIAN_TEXTURE = 7;
        public final int SHININESS_TEXTURE = 8;
    }

    public class Storage {

        public final int VERTEX = 0;
    }

    public class Uniform {

        public final int GLOBAL_MATRICES = 0;
        public final int PROJECTION = 1;
        public final int UNPROJECTION = 2;
        public final int MATERIAL = 3;
        public final int LIGHT = 4;

        public final int TRANSFORM0 = 1;
        public final int TRANSFORM1 = 2;
        public final int INDIRECTION = 3;
        public final int CONSTANT = 0;
        public final int PER_FRAME = 1;
        public final int PER_PASS = 2;
    }

    public class Vert {

        public final int POSITION = 0;
        public final int COLOR = 3;
        public final int TEXCOORD = 4;
        public final int INSTANCE = 7;
    }

    public class GammaBuffer {
        public final int VERTEX = 0;
        public final int PROJECTION = 1;
        public final int MAX = 2;
    }

    public class Texture {
        public final int NO_GAMMA = 0;
        public final int GAMMA = 1;
        public final int MAX = 2;
    }
    
    public final Attr Attr = new Attr();
    public final Buffer Buffer = new Buffer();
    public final Frag Frag = new Frag();
    public final Image Image = new Image();
    public final Object Object = new Object();
    public final Renderbuffer Renderbuffer = new Renderbuffer();
    public final Sampler Sampler = new Sampler();
    public final Storage Storage = new Storage();
    public final Uniform Uniform = new Uniform();
    public final Vert Vert = new Vert();
    
    public final GammaBuffer GammaBuffer = new GammaBuffer();
    public final Texture Texture = new Texture();
    
}
