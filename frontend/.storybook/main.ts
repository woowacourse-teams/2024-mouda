import type { StorybookConfig } from '@storybook/react-webpack5';
import path from 'path';
import { Configuration } from 'webpack';

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
        '@_routes': path.resolve(__dirname, 'src/routes'),
      };
    }

    config?.module?.rules?.push({
      test: /\.(ts|tsx)$/,
      loader: require.resolve('babel-loader'),
      options: {
        presets: [require.resolve('@emotion/babel-preset-css-prop')],
      },
    });

    return config;
  },
};

export default config;
