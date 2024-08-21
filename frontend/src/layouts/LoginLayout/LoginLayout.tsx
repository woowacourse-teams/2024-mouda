import { PropsWithChildren } from 'react';
import * as S from './LoginLayout.style';
import LoginHeader from './LoginHeader/LoginHeader';
import LoginMain from './LoginMain/LoginMain';
import LoginFooter from './LoginFooter/LoginFooter';

function LoginLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

LoginLayout.Header = LoginHeader;
LoginLayout.Main = LoginMain;
LoginLayout.Footer = LoginFooter;

export default LoginLayout;
