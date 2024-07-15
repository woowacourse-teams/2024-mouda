import { PropsWithChildren } from 'react';
import * as S from './MoimList.style';
import HomeHeader from './HomeHeader/HomeHeader';
import HomeMain from './HomeMain/HomeMain';
import HomeFixedButtonWrapper from './HomeFixedButtonWrapper/HomeFixedButtonWrapper';

function HomeLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.containerStyle}>{children}</div>;
}

HomeLayout.Header = HomeHeader;
HomeLayout.Main = HomeMain;
HomeLayout.HomeFixedButtonWrapper = HomeFixedButtonWrapper;

export default HomeLayout;
