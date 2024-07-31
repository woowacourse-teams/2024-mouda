import type { Meta, StoryObj } from '@storybook/react';

import { Fragment } from 'react';
import InformationContentContainer from './InformationLayoutContentContainer';
import { css } from '@emotion/react';

const meta: Meta<typeof InformationContentContainer> = {
  component: InformationContentContainer,
  decorators: (Story) => {
    return (
      <div
        css={css`
          border: solid 1px black;
        `}
      >
        <Story />
      </div>
    );
  },
};

export default meta;
type Story = StoryObj<typeof InformationContentContainer>;

export const Content: Story = {
  args: {
    children: (
      <Fragment>
        <div
          css={css`
            border: solid 1px red;
          `}
        >
          <h1>풋살 할 사람</h1>
          <h2>작성자 괘안나</h2>
        </div>
        <div
          css={css`
            border: solid 1px red;
          `}
        >
          <h3>모임 정보</h3>
          <h4>날짜</h4>
          <h4>시간</h4>
          <h4>장소</h4>
          <h4>최대인원</h4>
        </div>
      </Fragment>
    ),
  },
};
