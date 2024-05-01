import React from 'react';

function App() {
    return (
        <div className="container">
            <div className="row mt-5">
                <div className="col-12">
                    <div id="carouselExampleIndicators" className="carousel slide" data-ride="carousel">
                        <ol className="carousel-indicators">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" className="active"></li>
                        </ol>
                        <div className="carousel-inner">
                            <div className="carousel-item active">
                                <img className="d-block w-100" src="slide1.jpg" alt="First slide" />
                                <div className="carousel-caption d-none d-md-block">
                                    <h3>欢迎使用</h3>
                                    <p>XX医院医疗信息系统</p>
                                </div>
                            </div>
                        </div>
                        <a className="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span className="sr-only">Previous</span>
                        </a>
                        <a className="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                            <span className="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
            <div className="row text-center mt-4">
                <div className="col-md-4">
                    <img src="fast.jpg" alt="Column 1" style={{ width: '20%', marginBottom: '20px' }} />
                    <h5>方便快捷的线上挂号功能</h5>
                    <p>本系统提供线上挂号服务，无需排队等待，您可以随时随地通过我们的平台预约医生，提高就医效率。</p>
                </div>
                <div className="col-md-4">
                    <img src="safe.jpg" alt="Column 2" style={{ width: '18%', marginBottom: '20px' }} />
                    <h5>数据传输存储加密</h5>
                    <p>我们重视用户数据的安全性，采用先进的加密技术确保数据在传输和存储过程中的安全，保护用户隐私不被泄露。</p>
                </div>
                <div className="col-md-4">
                    <img src="user.jpg" alt="Column 3" style={{ width: '18%', marginBottom: '20px' }} />
                    <h5>用户友好的交互界面</h5>
                    <p>系统设计注重用户体验，操作便捷，使用户能够轻松地管理医疗信息。</p>
                </div>
            </div>
        </div>
    );
}

export default App;
