import { css } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const headerStyle = css`
  padding: 14px 22px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e0e0e0;
`;

export const headerTitleStyle = css`
  font-weight: 700;
  font-weight: 18px;
  color: rgba(118, 118, 118, 1);
`;

export const formStyle = css`
  padding: 0px 22px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 16px 0;
`;

export const inputStyle = css`
  padding: 8px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 16px;
`;

export const textareaStyle = css`
  padding: 8px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 16px;
  height: 100px;
  resize: none;
`;

export const bottomFixedStyle = css`
  position: fixed;
  bottom: 26px;
  padding: 0 16px;

  width: calc(100% - 32px); // 전체 너비에서 양쪽 패딩(16px * 2)을 뺀 값
`;

export const buttonStyle = css`
  width: 100%;
  padding: 12px;
  background-color: #007aff;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 18px;
  cursor: pointer;

  &:hover {
    background-color: #005bb5;
  }
`;

export const labelStyle = css`
  font-size: 14px;
  color: #555;
`;

export const requiredStyle = css`
  color: red;
  margin-left: 4px;
`;
