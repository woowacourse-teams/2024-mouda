import { css, Theme } from '@emotion/react';

// TODO: 바텀 버튼 UI에 대한 기획 논의 필요
export const bottomFixedStyle = (props: { theme: Theme }) => css`
  position: fixed;
  bottom: 26px;
  width: 100%;
  ${props.theme.layout.default};
  padding: 0 16px;
`;
