import { css } from '@emotion/react';

export const containerStyle = css`
  padding: 0px 22px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export const headerStyle = css`
  padding: 14px 0;
`;

export const logoStyle = css`
  font-size: 22px;
  font-weight: 700;
`;

export const mainStyle = css`
  display: flex;
  flex-direction: column;
  gap: 38px;
`;

export const navStyle = css`
  display: flex;
  gap: 12px;
`;

export const navItemStyle = css`
  color: rgba(71, 123, 255, 1);
  font-weight: 600;
  font-size: 18px;
  line-height: 22.88px;
  border-bottom: solid 2px;
`;

export const plusButtonStyle = css`
  position: fixed;
  bottom: 26px;
  right: 22px;

  // temp
  box-shadow: 0px 0px 12.6px 0px rgba(178, 178, 178, 0.33);
  background: white;
  color: rgba(71, 123, 255, 1);
  border: none;
  border-radius: 50%;
  width: 56px;
  height: 56px;
  font-size: 48px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

// temp
export const meetingCardStyle = css`
  background-color: #fff;
  border-radius: 8px;
  padding: 16px;
  margin: 16px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);

  h2 {
    font-size: 18px;
    margin: 0 0 8px 0;
  }

  p {
    margin: 4px 0;
    color: #666;
  }

  .date {
    color: #007aff;
  }
`;
