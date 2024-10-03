import { PropsWithChildren } from 'react';
import * as S from './LoginLayout.style';
import LoginMain from './LoginMain/LoginMain';
// import LoginFooter from './LoginFooter/LoginFooter';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import InformationBottomWrapper from '@_layouts/InformationLayout/InformationBottomWrapper/InformationBottomWrapper';

function LoginLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.layoutStyle}>{children}</div>;
}

LoginLayout.Header = StickyTriSectionHeader;
LoginLayout.Main = LoginMain;
LoginLayout.Footer = InformationBottomWrapper;

export default LoginLayout;
