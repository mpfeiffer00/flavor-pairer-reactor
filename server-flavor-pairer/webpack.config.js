var path = require('path');

module.exports = {
    entry: './src/main/js/app.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    resolve: {
       modules: [
         "node_modules",
         path.resolve(__dirname, "app")
       ],
       // directories where to look for modules
       extensions: [".js", ".json", ".jsx", ".css"]
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
              // Transform our own .css files with PostCSS and CSS-modules
              test: /\.css$/,
              exclude: /node_modules/,
              use: ['style-loader', 'css-loader'],
            }, {
              // Do not transform vendor's CSS with CSS-modules
              // The point is that they remain in global scope.
              // Since we require these CSS files in our JS or CSS files,
              // they will be a part of our compilation either way.
              // So, no need for ExtractTextPlugin here.
              test: /\.css$/,
              include: /node_modules/,
              use: ['style-loader', 'css-loader'],
            }
        ]
    }
};