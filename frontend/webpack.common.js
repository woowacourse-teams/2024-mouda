const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: './src/index.tsx',

  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, './src/index.html'),
      // templateParameters: { env: process.env.NODE_ENV || 'development' },
    }),
  ],
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js'],
    alias: {
      '@_apis': path.resolve(__dirname, 'src/apis'),
      '@_constants': path.resolve(__dirname, 'src/constants'),
      '@_common': path.resolve(__dirname, 'src/common'),
      '@_components': path.resolve(__dirname, 'src/components'),
      '@_hooks': path.resolve(__dirname, 'src/hooks'),
      '@_layouts': path.resolve(__dirname, 'src/layouts'),
      '@_pages': path.resolve(__dirname, 'src/pages'),
      '@_types': path.resolve(__dirname, 'src/types'),
      '@_utils': path.resolve(__dirname, 'src/utils'),
    },
  },
  module: {
    rules: [
      {
        test: /\.svg$/i,
        use: ['@svgr/webpack'],
      },
      {
        test: /\.(png|jpe?g|gif|webp)$/i,
        type: 'asset/resource',
      },
    ],
  },
};
