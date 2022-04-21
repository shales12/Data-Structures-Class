package art;

import java.awt.Color;

public class Collage {

    // The orginal picture
    private Picture originalPicture;

   
    private Picture collagePicture;

    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     */
    public Collage (String filename) {

        originalPicture = new Picture(filename);
        collageDimension =  4;
        tileDimension =  150;
        collagePicture = new Picture(tileDimension*collageDimension, tileDimension*collageDimension);
        
        for(int i = 0; i < tileDimension*collageDimension; i++) {
           for(int j = 0; j < tileDimension*collageDimension; j++) {
               int si = i * originalPicture.width() / (tileDimension*collageDimension);
               int sj = j * originalPicture.height() / (tileDimension*collageDimension);
               Color color = originalPicture.get(si, sj);
               collagePicture.set(i, j, color);
           }

       }
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     */    
    public Collage (String filename, int td, int cd) {


        collageDimension = cd;
        tileDimension = td;
        int dim = tileDimension*collageDimension;

        originalPicture = new Picture(filename);
        collagePicture = new Picture(dim, dim);
        
        for(int i = 0; i < dim; i++) {
           for(int j = 0; j < dim; j++) {
               int si = i * originalPicture.width() / dim;
               int sj = j * originalPicture.height() /dim;
               Color color = originalPicture.get(si, sj);
               collagePicture.set(i, j, color);
           }
       }
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
    
     */
    public static void scale (Picture source, Picture target) {

        // WRITE YOUR CODE HERE
        
        int width  = target.width();
        int height = target.height();
        

        for (int targetCol = 0; targetCol < width; targetCol++) {
            for (int targetRow = 0; targetRow < height; targetRow++) {
                int sourceCol = targetCol * source.width()  / width;
                int sourceRow = targetRow * source.height() / height;
                Color color = source.get(sourceCol, sourceRow);
                target.set(targetCol, targetRow, color);
            }
        }


        


    }

     /*
     * Returns the collageDimension instance variable
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
          */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable

     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
 
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {

        
        collageDimension = getCollageDimension();
        tileDimension = getTileDimension();
        int dim = tileDimension*collageDimension;
        int height = tileDimension;
        int width  = tileDimension;
        int m = collageDimension;
        int n = collageDimension;



        collagePicture = getCollagePicture();
        Picture tile = new Picture(tileDimension,tileDimension);
        scale(originalPicture, tile);
        for(int i = 0; i < tileDimension; i++) {
            for(int j = 0; j < tileDimension; j++) {
                int si = i * collagePicture.width() / tileDimension;
                int sj = j * collagePicture.height() / tileDimension;
                Color color = collagePicture.get(si, sj);
                tile.set(i, j, color);
           }

       }
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = tile.get(col, row);
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        collagePicture.set(width*j + col, height*i + row, color);

                   }
               }
           }
       }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

        getTileDimension();
        int width  = tileDimension;
        int height = tileDimension;
 
        if (component.contains("red")) {
 
            for (int col = 0; col < width; col++) {
                for (int row = 0; row < height; row++) {
 
                    for (int i = collageRow; i < collageRow + 1; i++) {
                        for (int j = collageCol; j < collageCol + 1; j++) {
                            Color color = collagePicture.get(tileDimension*j + col, tileDimension*i + row);
                            int r = color.getRed();
                            collagePicture.set(tileDimension * j + col,tileDimension * i + row, new Color(r, 0, 0));
                        }
                    }
                }
            }
        } else if(component.contains("green")) {
            for (int col = 0; col < width; col++) {
                for (int row = 0; row < height; row++) {
 
                    for (int i = collageRow; i < collageRow + 1; i++) {
                        for (int j = collageCol; j < collageCol + 1; j++) {
                            Color color = collagePicture.get(tileDimension*j + col, tileDimension*i + row);
                            int g = color.getGreen();
                            collagePicture.set(tileDimension * j + col,tileDimension * i + row, new Color(0, g, 0));
                        }
                    }
                }
            }
        } else if(component.contains("blue")) {
            for (int col = 0; col < width; col++) {
                for (int row = 0; row < height; row++) {
 
                    for (int i = collageRow; i < collageRow + 1; i++) {
                        for (int j = collageCol; j < collageCol + 1; j++) {
                            Color color = collagePicture.get(tileDimension*j + col, tileDimension*i + row);
                            int b = color.getBlue();
                            collagePicture.set(tileDimension * j + col,tileDimension * i + row, new Color(0, 0, b));
                        }
                    }
                }
            }
 
        }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
    
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        getTileDimension();
        int height = tileDimension;
        int width  = tileDimension;
        int m = collageDimension;
        int n = collageDimension;
        Picture newTile = new Picture(filename);
        collagePicture = getCollagePicture();
        Picture tile = new Picture(tileDimension , tileDimension);
        for(int i = 0; i < tileDimension; i++) {
            for(int j = 0; j < tileDimension; j++) {
                int si = i * newTile.width() / tileDimension;
                int sj = j * newTile.height() / tileDimension;
                Color color = newTile.get(si, sj);
                tile.set(i, j, color);
            }
 
        }
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = tile.get(col, row);
                for (int i = collageRow; i < collageRow + 1; i++) {
                    for (int j = collageCol; j < collageCol + 1; j++) {
                        collagePicture.set(width*j + col, height*i + row, color);
                    }
                }
            }
        }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
 
     */
    public void grayscaleTile (int collageCol, int collageRow) {

        getTileDimension();
        for (int col = 0; col < tileDimension; col++) {
            for (int row = 0; row < tileDimension; row++) {
 
                for (int i = collageRow; i < collageRow + 1; i++) {
                    for (int j = collageCol; j < collageCol + 1; j++) {
                        Color color = collagePicture.get(tileDimension*j + col, tileDimension*i + row);
                        Color gray = toGray(color);
                        collagePicture.set(tileDimension * j + col,tileDimension * i + row,gray);
                    }
                }
            }
        }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
 
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
