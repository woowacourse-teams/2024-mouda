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
  performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000,
  },
  optimization: {
    minimize: true,
    minimizer: [
      new TerserPlugin({
        parallel: true, // 병렬 처리 활성화
        terserOptions: {
          compress: {
            drop_console: true, // 콘솔 로그 제거
          },
        },
      }),
    ],
    splitChunks: {
      chunks: 'all', // 모든 타입의 청크를 분할
      maxInitialRequests: 3,
      maxAsyncRequests: 5,
      minSize: 30000,
    },
    runtimeChunk: 'single', // 런타임 청크를 분리
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
