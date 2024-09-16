const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const { sentryWebpackPlugin } = require('@sentry/webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'source-map',
  module: {
    rules: [
      {
        test: /\.(ts|tsx)$/,
        exclude: /node_modules/,
        use: ['babel-loader', 'ts-loader'],
      },
    ],
  },
  plugins: [
    // Put the Sentry Webpack plugin after all other plugins
    sentryWebpackPlugin({
      authToken: process.env.SENTRY_AUTH_TOKEN,
      org: '2024-mouda',
      project: 'javascript-react',
    }),
    
    new CopyWebpackPlugin({
      patterns: [
        { from: 'public', to: '' }, // public 폴더의 모든 파일을 dist 폴더의 루트로 복사
      ],
    }),
  ],
});
