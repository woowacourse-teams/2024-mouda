import { Configuration } from 'webpack';
import type { StorybookConfig } from '@storybook/react-webpack5';
import path from 'path';

const config: StorybookConfig = {
  stories: ['../src/**/*.mdx', '../src/**/*.stories.@(js|jsx|mjs|ts|tsx)'],
  addons: [
    '@storybook/addon-webpack5-compiler-swc',
    '@storybook/addon-onboarding',
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    '@chromatic-com/storybook',
    '@storybook/addon-interactions',
  ],
  framework: {
    name: '@storybook/react-webpack5',
    options: {
      builder: {
        useSWC: true,
      },
    },
  },
  swc: () => ({
    jsc: {
      transform: {
        react: {
          runtime: 'automatic',
        },
      },
    },
  }),
  webpackFinal: async (config: Configuration) => {
    const { resolve } = config;

    if (resolve) {
      resolve.alias = {
        ...resolve.alias,
        '@_apis': path.resolve(__dirname, '../src/apis'),
        '@_constants': path.resolve(__dirname, '../src/constants'),
        '@_common': path.resolve(__dirname, '../src/common'),
        '@_components': path.resolve(__dirname, '../src/components'),
        '@_hooks': path.resolve(__dirname, '../src/hooks'),
        '@_layouts': path.resolve(__dirname, '../src/layouts'),
        '@_pages': path.resolve(__dirname, '../src/pages'),
        '@_types': path.resolve(__dirname, '../src/types'),
        '@_utils': path.resolve(__dirname, '../src/utils'),
      };
    }

    config?.module?.rules?.push({
      test: /\.(ts|tsx)$/,
      loader: require.resolve('babel-loader'),
      options: {
        presets: [require.resolve('@emotion/babel-preset-css-prop')],
      },
    });

    if (config.module?.rules) {
      config.module = config.module || {};
      config.module.rules = config.module.rules || [];

      const imageRule = config.module.rules.find((rule) =>
        rule?.['test']?.test('.svg'),
      );
      if (imageRule) {
        imageRule['exclude'] = /\.svg$/;
      }

      config.module.rules.push({
        test: /\.svg$/,
        use: ['@svgr/webpack'],
      });
    }

    return config;
  },
};

export default config;
