const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const dotenv = require('dotenv');
const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');

dotenv.config();

module.exports = {
  entry: './src/index.tsx',

  output: {
    filename: '[name].[contenthash].js', // 캐시를 위한 해시 추가
    path: path.resolve(__dirname, 'dist'),
    clean: true,
    publicPath: '/',
  },
  devServer: {
    historyApiFallback: true,
    open: true,
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, './src/index.html'),
    }),
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
    }),
    new CopyWebpackPlugin({
      patterns: [
        { from: 'public/firebase-messaging-sw.js', to: 'firebase-messaging-sw.js' },
        { from: 'public/firebaseConfig.js', to: 'firebaseConfig.js' },
      ],
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
      '@_routes': path.resolve(__dirname, 'src/routes'),
      '@_mocks': path.resolve(__dirname, 'src/mocks'),
      '@_service': path.resolve(__dirname, 'src/service'),
    },
  },
  module: {
    rules: [
      {
        test: /\.svg$/i,
        oneOf: [
          {
            use: ['@svgr/webpack'],
            issuer: /\.[jt]sx?$/,
            resourceQuery: { not: [/url/] },
          },
          {
            type: 'asset/resource',
            resourceQuery: /url/,
          }
        ]
      },
      {
        test: /\.(png|jpe?g|gif|webp|woff2)$/i,
        type: 'asset/resource',
      },
    ],
  },
};
